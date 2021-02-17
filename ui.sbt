import scala.sys.process.Process

val Success = 0
val Error = 1

// Run angular serve task when Play runs in dev mode, that is, when using 'sbt run'
// https://www.playframework.com/documentation/2.8.x/SBTCookbook
PlayKeys.playRunHooks += baseDirectory.map(FrontendRunHook.apply).value

val isWindows = System.getProperty("os.name").toLowerCase().contains("win")

// Execute on commandline, depending on the operating system. Used to execute npm commands.
def runOnCommandline(script: String)(implicit dir: File): Int = {
  if (isWindows) {
    Process("cmd /c " + script, dir)
  } else {
    Process(script, dir)
  }
} !

def isNodeModulesInstalled(implicit dir: File): Boolean = (dir / "node_modules").exists()

def runNpmInstall(implicit dir: File): Int =
  if (isNodeModulesInstalled) Success else runOnCommandline(FrontendCommands.dependencyInstall)

def ifNodeModulesInstalled(task: => Int)(implicit dir: File): Int =
  if (runNpmInstall == Success) task
  else Error

def executeUiTests(implicit dir: File): Int = ifNodeModulesInstalled(runOnCommandline(FrontendCommands.test))

def executeProdBuild(implicit dir: File): Int = ifNodeModulesInstalled(runOnCommandline(FrontendCommands.build))


// Create frontend build tasks for prod, dev and test execution.

lazy val `ui-test` = taskKey[Unit]("Run UI tests when testing application.")

`ui-test` := {
  implicit val userInterfaceRoot: File = baseDirectory.value / "ui"
  if (executeUiTests != Success) throw new Exception("UI tests failed!")
}

lazy val `ui-prod-build` = taskKey[Unit]("Run UI build when packaging the application.")

`ui-prod-build` := {
  implicit val userInterfaceRoot: File = baseDirectory.value / "ui"
  if (executeProdBuild != Success) throw new Exception("Oops! UI Build crashed.")
}

// Execute frontend prod build task prior to play dist execution.
dist := (dist dependsOn `ui-prod-build`).value

// Execute frontend prod build task prior to play stage execution.
stage := (stage dependsOn `ui-prod-build`).value

// Execute frontend test task prior to play test execution.
test := ((test in Test) dependsOn `ui-test`).value

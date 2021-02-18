import com.typesafe.sbt.packager.docker._
import com.typesafe.sbt.packager.linux.LinuxPlugin.autoImport.daemonUser
import sbt.Keys.javaOptions

lazy val serviceName = "platform-template"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    scalaVersion := "2.13.1",
    organization := "com.thiefspin",
    name := serviceName,
    dockerSettings,
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      "org.playframework.anorm" %% "anorm" % "2.6.5",
      "org.postgresql" % "postgresql" % "42.2.5",
      "commons-codec" % "commons-codec" % "1.14",
      "org.mindrot" % "jbcrypt" % "0.4",
      "commons-codec" % "commons-codec" % "1.14",
      "org.bouncycastle" % "bcprov-jdk16" % "1.46",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    ),
    javaOptions in Universal ++= Seq(
      "-Dpidfile.path=/dev/null",
      "-J-Xtune:virtualized",
      "-J-XX:InitialRAMPercentage=20",
      "-J-XX:MaxRAMPercentage=75",
      "-J-Xjit:enableSelfTuningScratchMemoryUsageBeforeCompile",
      "-Dcom.ibm.tools.attach.enable=no",
      "-J-Xss128k",
      "-J-XX:+ClassRelationshipVerifier",
      "-J-Xcompressedrefs",
      "-J-XX:ActiveProcessorCount=4",
      "-J-XX:+UseContainerSupport"
    )
  )

lazy val dockerSettings = List(
  dockerCommands ++= Seq(
    ExecCmd("RUN",
      "chmod", "u+x",
      s"${(defaultLinuxInstallLocation in Docker).value}/bin/${executableScriptName.value}")
  ),
  mappings in Docker += file("startup.sh") -> s"${(defaultLinuxInstallLocation in Docker).value}/bin/startup.sh",
  dockerExposedVolumes := Seq(s"/var/log/$serviceName"),
  dockerExposedPorts := Seq(9000),
  dockerEntrypoint := Seq("sh", "/opt/docker/bin/startup.sh"),
  dockerBaseImage := "adoptopenjdk/openjdk8-openj9:slim",
  daemonUserUid in Docker := None,
  daemonUser in Docker := "daemon",
  version in Docker := version.value
)

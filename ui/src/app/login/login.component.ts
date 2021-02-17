import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../authentication.service';
import {LoginRequest} from '../auth/LoginRequest';
import {Session} from '../auth/Session';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthenticationService
  ) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get controls() {
    return this.loginForm.controls;
  }

  onSubmit() {

    this.authService.login(
      new LoginRequest(
        this.controls.email.value,
        this.controls.password.value
      )
    ).subscribe((data: Session) => {
      console.log(data);
      localStorage.setItem('session', JSON.stringify(data));
      this.router.navigate(['/home']);
    });
  }

}

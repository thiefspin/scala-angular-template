import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {LoginRequest} from '../auth/LoginRequest';
import {Session} from '../auth/Session';
import {AuthenticationService} from '../authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  searchForm: FormGroup;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit() {
    this.searchForm = this.formBuilder.group({
      text: ['', Validators.required]
    });
  }

  get controls() {
    return this.searchForm.controls;
  }

  onSubmit() {
    console.log('You searched for: ' + this.controls.text.value);
  }

}

import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { DataService } from '../services/data.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  isAuthenticated : boolean = false;
  constructor(private dataService : DataService, private router : Router){}

  ngOnInit(): void {
    this.dataService.isAuthenticated.subscribe(val => this.isAuthenticated = val);
  }

  handleLogout() {
    window.localStorage.clear();
    this.dataService.setAuthenticated(false);
    this.router.navigate(["/login"]);
  }
}

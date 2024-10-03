import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { map, Observable } from 'rxjs';
import { baseUrls } from '../constants/constants';
import { DahsboardService } from '../services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit{
  constructor(private dashboardService: DahsboardService) {}

  username! : any;

  ngOnInit(): void {
    this.dashboardService.getUserDetails("hamza.hamdan@hotmail.com").subscribe(res => this.username = res);
  }
  
}

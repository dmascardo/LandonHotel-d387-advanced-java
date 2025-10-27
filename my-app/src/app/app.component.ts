import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private httpClient: HttpClient) {}
  
  private baseURL: string = 'http://localhost:8080';
  
  roomsearch!: FormGroup;
  rooms: any[] = [];
  request: any;
  currentCheckInVal: string = '';
  currentCheckOutVal: string = '';

  englishMessage: string = 'Loading...';
  frenchMessage: string = 'Loading...';
  presentationTimes: any = { ET: 'Loading...', MT: 'Loading...', UTC: 'Loading...' };

  ngOnInit() {
    this.httpClient.get<any>(this.baseURL + '/api/welcome').subscribe(r => { 
      this.englishMessage = r.english; 
      this.frenchMessage = r.french; 
    });
    this.httpClient.get<any>(this.baseURL + '/api/hotel-presentation-times').subscribe(d => this.presentationTimes = d);
    this.roomsearch = new FormGroup({ checkin: new FormControl(''), checkout: new FormControl('') });
    this.roomsearch.valueChanges.subscribe(x => { this.currentCheckInVal = x.checkin; this.currentCheckOutVal = x.checkout; });
  }

  onSubmit(formGroup: any) {
    this.httpClient.get(this.baseURL + '/room/reservation/v1?checkin=' + this.currentCheckInVal + '&checkout=' + this.currentCheckOutVal).subscribe((rooms: any) => { this.rooms = Object.values(rooms)[0] as any[]; });
  }

  reserveRoom(value: string) {}
}

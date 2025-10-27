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
  rooms: Room[] = [];
  request!: ReserveRoomRequest;
  currentCheckInVal: string = '';
  currentCheckOutVal: string = '';

  englishMessage: string = 'Loading...';
  frenchMessage: string = 'Loading...';
  presentationTimes: { ET: string; MT: string; UTC: string } = {
    ET: 'Loading...',
    MT: 'Loading...',
    UTC: 'Loading...'
  };

  ngOnInit() {
    this.httpClient.get<{ english: string; french: string }>(this.baseURL + "/api/welcome")
      .subscribe({
        next: (response) => {
          this.englishMessage = response.english;
          this.frenchMessage = response.french;
        },
        error: (err) => console.error("Error fetching welcome:", err)
      });

    this.httpClient.get<{ ET: string; MT: string; UTC: string }>(this.baseURL + "/api/hotel-presentation-times")
      .subscribe({
        next: (data) => {
          this.presentationTimes = data;
        },
        error: (err) => console.error("Error fetching times:", err)
      });

    this.roomsearch = new FormGroup({
      checkin: new FormControl(''),
      checkout: new FormControl('')
    });

    this.roomsearch.valueChanges.subscribe(x => {
      this.currentCheckInVal = x.checkin || '';
      this.currentCheckOutVal = x.checkout || '';
    });
  }

  onSubmit(formGroup: FormGroup) {
    this.getAll().subscribe(rooms => {
      this.rooms = <Room[]>Object.values(rooms)[0];
    });
  }

  reserveRoom(value: string) {
    this.request = new ReserveRoomRequest(value, this.currentCheckInVal, this.currentCheckOutVal);
    this.createReservation(this.request);
  }

  createReservation(body: ReserveRoomRequest) {
    const options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };
    this.httpClient.post(this.baseURL + '/room/reservation/v1', body, options)
      .subscribe(res => console.log(res));
  }

  getAll(): Observable<any> {
    return this.httpClient.get(
      this.baseURL + '/room/reservation/v1?checkin=' + this.currentCheckInVal + '&checkout=' + this.currentCheckOutVal,
      { responseType: 'json' }
    );
  }
}

export interface Roomsearch {
  checkin: string;
  checkout: string;
}

export interface Room {
  id: string;
  roomNumber: string;
  price: string;
  links: string;
}

export class ReserveRoomRequest {
  roomId: string;
  checkin: string;
  checkout: string;

  constructor(roomId: string, checkin: string, checkout: string) {
    this.roomId = roomId;
    this.checkin = checkin;
    this.checkout = checkout;
  }
}

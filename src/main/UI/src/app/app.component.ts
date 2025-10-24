import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private httpClient: HttpClient) {}

  private baseURL: string = 'http://localhost:8080';
  private getUrl: string = this.baseURL + '/room/reservation/v1/';
  private postUrl: string = this.baseURL + '/room/reservation/v1';

  public submitted!: boolean;
  roomsearch!: FormGroup;
  rooms!: Room[];
  request!: ReserveRoomRequest;
  currentCheckInVal!: string;
  currentCheckOutVal!: string;

  englishMessage: string = '';
  frenchMessage: string = '';
  presentationTimes: { ET: string; MT: string; UTC: string } = { ET: '', MT: '', UTC: '' };

  ngOnInit() {
    this.httpClient.get<{ english: string; french: string }>("http://localhost:8080/api/welcome")
      .subscribe({
        next: (response) => {
          console.log("Welcome response:", response);
          this.englishMessage = response.english;
          this.frenchMessage = response.french;
        },
        error: (err) => console.error("Error fetching welcome messages", err)
      });

    this.httpClient.get<{ ET: string; MT: string; UTC: string }>("http://localhost:8080/api/hotel-presentation-times")
      .subscribe({
        next: (data) => {
          this.presentationTimes = data;
          console.log("Time zone response:", data);
        },
        error: (err) => console.error("Error fetching presentation times", err)
      });

    this.roomsearch = new FormGroup({
      checkin: new FormControl(''),
      checkout: new FormControl('')
    });

    const roomsearchValueChanges$ = this.roomsearch.valueChanges;
    roomsearchValueChanges$.subscribe(x => {
      this.currentCheckInVal = x.checkin;
      this.currentCheckOutVal = x.checkout;
    });
  }

  onSubmit({ value, valid }: { value: Roomsearch, valid: boolean }) {
    this.getAll().subscribe(
      rooms => {
        console.log(Object.values(rooms)[0]);
        this.rooms = <Room[]>Object.values(rooms)[0];
      }
    );
  }

  reserveRoom(value: string) {
    this.request = new ReserveRoomRequest(value, this.currentCheckInVal, this.currentCheckOutVal);
    this.createReservation(this.request);
  }

  createReservation(body: ReserveRoomRequest) {
    let bodyString = JSON.stringify(body);
    const options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    this.httpClient.post(this.postUrl, body, options)
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


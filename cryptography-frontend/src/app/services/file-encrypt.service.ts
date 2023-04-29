import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {FileToEncrypt} from "../model/file-to-encrypt";

@Injectable({
  providedIn: 'root'
})
export class FileEncryptService {

  private baseUrl = 'http://localhost:8081';

  constructor(private http: HttpClient) { }

  encrypt(fileToEncrypt: FileToEncrypt): Observable<any> {
    return this.http.post<FileToEncrypt>(`${this.baseUrl}/encrypt`, fileToEncrypt);
  }

}

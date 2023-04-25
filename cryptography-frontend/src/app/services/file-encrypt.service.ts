import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {FileModel} from "../model/file-model";

@Injectable({
  providedIn: 'root'
})
export class FileEncryptService {

  private baseUrl = 'http://localhost:8081';

  constructor(private http: HttpClient) { }

  encrypt(fileToEncrypt: FileModel): Observable<any> {
    return this.http.post<FileModel>(`${this.baseUrl}/encrypt`, fileToEncrypt);
  }

}

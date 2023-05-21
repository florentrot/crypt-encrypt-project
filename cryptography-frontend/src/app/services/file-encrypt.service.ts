import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {FileEncryptDto} from "../dto/file-encrypt-dto";

@Injectable({
  providedIn: 'root'
})
export class FileEncryptService {

  private baseUrl = 'http://localhost:8081';

  constructor(private http: HttpClient) { }

  encrypt(fileToEncrypt: FileEncryptDto): Observable<any> {
    return this.http.post<FileEncryptDto>(`${this.baseUrl}/encrypt`, fileToEncrypt);
  }

}

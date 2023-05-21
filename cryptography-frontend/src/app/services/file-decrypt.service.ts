import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {FileDecryptDto} from "../dto/file-decrypt-dto";

@Injectable({
  providedIn: 'root'
})
export class FileDecryptService {

  private baseUrl = 'http://localhost:8081';
  constructor(private http: HttpClient) { }

  decrypt(decryptComponentsDTO: FileDecryptDto): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file_upload', decryptComponentsDTO.file, decryptComponentsDTO.file.name);
    formData.append('decrypt_model', JSON.stringify(decryptComponentsDTO));
    return this.http.post<FileDecryptDto>(`${this.baseUrl}/decrypt`, formData);
  }
}

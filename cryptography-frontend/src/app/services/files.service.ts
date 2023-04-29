import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from "@angular/common/http";
import {FileToEncrypt} from "../model/file-to-encrypt";
import {DecryptedFile} from "../model/decrypted-file";

@Injectable({
  providedIn: 'root'
})
export class FilesService {

  private baseUrl = 'http://localhost:8081';

  constructor(private http: HttpClient) {
  }

  uploadFile(fileToEncrypt: FileToEncrypt): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file_upload', fileToEncrypt.file, fileToEncrypt.file.name);
    formData.append('file_model', JSON.stringify(fileToEncrypt));
    return this.http.post(`${this.baseUrl}/upload`, formData);
  }


  getAllPersistentFiles(): Observable<FileToEncrypt[]> {
    return this.http.get<FileToEncrypt[]>(`${this.baseUrl}/encryptedFiles`);
  }

  getAllDecryptedFiles(): Observable<DecryptedFile[]> {
    return this.http.get<FileToEncrypt[]>(`${this.baseUrl}/decryptedFiles`);
  }

}

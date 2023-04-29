import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from "@angular/common/http";
import {FileToEncrypt} from "../model/file-to-encrypt";
import {FileToDecrypt} from "../model/decrypted-file-model";

@Injectable({
  providedIn: 'root'
})
export class FilesService {

  private baseUrl = 'http://localhost:8081';

  constructor(private http: HttpClient) {
  }

  uploadFile(fileModel: FileToEncrypt): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file_upload', fileModel.file, fileModel.file.name);
    formData.append('file_model', JSON.stringify(fileModel));
    return this.http.post(`${this.baseUrl}/upload`, formData);
  }


  getAllPersistentFiles(): Observable<FileToEncrypt[]> {
    return this.http.get<FileToEncrypt[]>(this.baseUrl);
  }

  getAllDecryptedFiles(): Observable<FileToDecrypt[]> {
    return this.http.get<FileToEncrypt[]>(`${this.baseUrl}/decryptedFiles`);
  }

}

import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from "@angular/common/http";
import {FileEncryptDto} from "../dto/file-encrypt-dto";
import {DecryptedFile} from "../model/decrypted-file";
import {EncryptedFile} from "../model/encrypted-file";

@Injectable({
  providedIn: 'root'
})
export class FilesService {

  private baseUrl = 'http://localhost:8081';

  constructor(private http: HttpClient) {
  }

  uploadFile(fileToEncrypt: FileEncryptDto): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file_upload', fileToEncrypt.file, fileToEncrypt.file.name);
    formData.append('file_model', JSON.stringify(fileToEncrypt));
    return this.http.post(`${this.baseUrl}/upload`, formData);
  }


  getAllPersistentFiles(): Observable<EncryptedFile[]> {
    return this.http.get<EncryptedFile[]>(`${this.baseUrl}/encryptedFiles`);
  }

  getAllDecryptedFiles(): Observable<DecryptedFile[]> {
    return this.http.get<DecryptedFile[]>(`${this.baseUrl}/decryptedFiles`);
  }

}

import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from "@angular/common/http";
import {FileModel} from "../model/file-model";
import {DecryptedFiles} from "../model/decrypted-file-model";

@Injectable({
  providedIn: 'root'
})
export class FilesService {

  private baseUrl = 'http://localhost:8081';

  constructor(private http: HttpClient) {
  }

  uploadFile(fileModel: FileModel): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file_upload', fileModel.file, fileModel.file.name);
    formData.append('file_model', JSON.stringify(fileModel));
    return this.http.post(`${this.baseUrl}/upload`, formData);
  }


  getAllPersistentFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(this.baseUrl);
  }

  getAllDecryptedFiles(): Observable<DecryptedFiles[]> {
    return this.http.get<FileModel[]>(`${this.baseUrl}/decryptedFiles`);
  }

}

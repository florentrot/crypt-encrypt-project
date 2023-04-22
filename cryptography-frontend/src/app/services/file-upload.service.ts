import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from "@angular/common/http";
import {FileModel} from "../model/file-model";

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  uploadFile(fileModel: FileModel): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file_upload', fileModel.file, fileModel.file.name);
    formData.append('file_model', JSON.stringify(fileModel));

    return this.http.post(`${this.baseUrl}/upload`, formData);
  }


  getAllFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(this.baseUrl);
  }

}

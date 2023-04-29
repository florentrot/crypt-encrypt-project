import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FilesService} from "../../services/files.service";
import {FileModel} from "../../model/file-model";
import {FileEncryptService} from "../../services/file-encrypt.service";
import {v4 as uuidv4} from 'uuid';
import {ConfirmButton} from "../ag-grid-component/button-cell-confirm.components";
import {DeleteButton} from "../ag-grid-component/button-cell-delete.components";
import {EncryptButton} from "../ag-grid-component/button-cell-encrypt.components";
import {AgGridAngular} from "ag-grid-angular";
import {ColumnApi, GridApi, GridOptions} from "ag-grid-community";
import {EncryptedButton} from "../ag-grid-component/button-cell-encrypted.components";
import {Subject, tap} from "rxjs";


@Component({
  selector: 'app-encrypt',
  templateUrl: './encrypt.component.html',
  styleUrls: ['./encrypt.component.css']
})
export class EncryptComponent implements OnInit {
  @ViewChild("fileToEncrypt") fileToEncrypt: ElementRef;
  @ViewChild("recipientsEmail") recipientsEmail: ElementRef;
  @ViewChild("agGrid") agGrid: AgGridAngular;

  isConfirmDisabled: boolean = true;
  isAnyError: boolean = false;
  isFileSaved: boolean = false;
  isClearDisabled: boolean = true;
  isEncryptionDone: boolean = false;

  selectedFile: File = null;
  unsavedFiles = new Array<FileModel>();
  rowData: any[];
  columnApi: ColumnApi;
  gridApi: GridApi;

  private dataUpdated$ = new Subject<void>();

  gridOptions: GridOptions = {
    columnDefs: [
      {headerName: "#", valueGetter: "node.rowIndex + 1", width: 60},
      {headerName: "file name", field: "fileName", width: 150},
      {headerName: "recipient's email", field: "recipientsEmail", width: 150}, // added now
      {headerName: "size", field: "fileSize", width: 60},
      {headerName: "extension", field: "fileExtension", width: 100},
      {
        headerName: "Action",
        cellRendererSelector: params => {
          if (params.data.fileStatus === 'Saved') {
            return {component: EncryptButton}
          } else if (params.node.data.fileStatus === 'Unconfirmed') {
            return {component: ConfirmButton}
          } else {
            return {component: EncryptedButton}
          }
        },
        width: 140,
        cellRendererParams: {
          clicked: (data: any) => {
            if (data.fileStatus === "Unconfirmed") {
              this.onSaveFile(data);
            } else {
              this.onEncrypt(data);
            }
          }
        },
      },
      {
        headerName: "Delete",
        cellRenderer: DeleteButton,
        width: 120,
        cellRendererParams: {
          clicked: (data: any) => {
            if (data.fileStatus === 'Unconfirmed') {
              this.onRemoveRow(data);
            } else {
              alert("You try to delete persistent data");
            }
          }
        },
      },
      {headerName: "Status", field: "fileStatus", width: 110}
    ]
  };

  onGridReady = (params) => {
    this.gridApi = params.api;
    this.columnApi = params.columnApi;
    this.gridApi.sizeColumnsToFit();
    this.getAllData();
  }

  ngOnInit(): void {
    this.isConfirmDisabled = true;
    this.dataUpdated$.subscribe(() => {
      this.getAllData();
    })
  }

  ngOnDestroy() {
    this.dataUpdated$.unsubscribe();
  }

  constructor(private fileUploadService: FilesService,
              private fileEncryptService: FileEncryptService) {
  }

  insertFile(event: any) {
    if (this.unsavedFiles.length === 0) {
      this.selectedFile = event.target.files[0];
      this.addFileModel(this.selectedFile);
      this.checkValidForm();
      this.isClearDisabled = false;
    } else {
      this.isAnyError = true;
    }
    setTimeout(() => {
      this.isAnyError = false;
    }, 5000)
  }

  private addFileModel(file: File) {
    let fileModel = new FileModel();
    fileModel.fileId = uuidv4();
    fileModel.fileName = file.name.substring(0, file.name.lastIndexOf("."));
    fileModel.fileSize = file.size;
    fileModel.fileType = file.type;
    fileModel.fileExtension = file.name.substring(file.name.lastIndexOf("."), file.name.length);
    fileModel.fileStatus = "Unconfirmed";
    fileModel.file = file;
    this.unsavedFiles.push(fileModel);
  }

  clearForm() {
    this.fileToEncrypt.nativeElement.value = null;
    this.recipientsEmail.nativeElement.value = "";
    if (this.selectedFile !== null) {
      this.selectedFile = null;
    }
    this.isConfirmDisabled = true;
    this.isClearDisabled = true;
    this.isAnyError = false;
    this.onClearRows();
  }

  onConfirmForm() {
    this.unsavedFiles[0].recipientsEmail = this.recipientsEmail.nativeElement.value;
    this.onAddRow(this.unsavedFiles[0]);
    console.log(this.unsavedFiles);

    this.fileToEncrypt.nativeElement.value = null;
    this.recipientsEmail.nativeElement.value = "";

    this.isConfirmDisabled = true;
    this.isClearDisabled = true;
  }

  checkValidForm() {
    // CHECK FOR ConfirmButton
    if (this.selectedFile !== null && this.recipientsEmail.nativeElement.value.length) {
      this.isConfirmDisabled = false;
    } else {
      this.isConfirmDisabled = true;
    }

    // CHECK FOR ClearButton
    if(this.selectedFile !== null || this.recipientsEmail.nativeElement.value.length) {
      this.isClearDisabled = false;
    } else {
      this.isClearDisabled = true;
    }
  }

  onSaveFile(fileModel: FileModel) {
    this.fileUploadService.uploadFile(fileModel).subscribe(() => {
      this.getAllData();
      this.refreshData();
    });

    this.isFileSaved = true;

    this.unsavedFiles = this.unsavedFiles.filter(data => data != fileModel);
    this.rowData = this.rowData.filter(data => data !== fileModel);

    this.fileToEncrypt.nativeElement.value = null;
    this.recipientsEmail.nativeElement.value = "";

    this.selectedFile = null;

    this.isConfirmDisabled = true;
    setTimeout(() => {
      this.isFileSaved = false;
    }, 5000)
  }

  onEncrypt(fileModel: FileModel) {
    this.fileEncryptService.encrypt(fileModel).subscribe(() => {
      this.getAllData();
      this.refreshData()
    });
    this.isEncryptionDone = true;

    setTimeout(() => {
      this.isEncryptionDone = false;
    }, 5000)
  }

  onAddRow(fileModel: FileModel): void {
    this.rowData.push(fileModel);
    this.gridApi.applyTransaction({
        add: [fileModel]
      }
    );
  }

  onRemoveRow(fileModel: FileModel): void {
    this.rowData = this.rowData.filter(data => data !== fileModel);
    console.log(this.rowData);
    this.unsavedFiles = this.unsavedFiles.filter(data => data !== fileModel);
    this.selectedFile = null;
    this.gridApi.applyTransaction({
        remove: [fileModel]
      }
    );
    if (this.unsavedFiles.length === 0) {
      this.isClearDisabled = true;
      this.isConfirmDisabled = true;
    }
    this.fileToEncrypt.nativeElement.value = null;
    this.recipientsEmail.nativeElement.value = "";
  }

  onClearRows(): void {
    if (this.unsavedFiles.length > 0) {
      this.unsavedFiles.forEach(unsavedFile => {
        this.rowData = this.rowData.filter(data => data !== unsavedFile);
      });
    }
    this.unsavedFiles = [];
  }

  getAllData(): void {
    this.fileUploadService.getAllPersistentFiles().pipe(
      tap(data => {
        if (this.unsavedFiles.length) {
          data.push(this.unsavedFiles[0]);
        }
      })
    ).subscribe(data => {
      this.rowData = data;
    });
  }

  refreshData() {
    this.gridApi.refreshCells();
    if (this.unsavedFiles.length === 0) {
      this.isClearDisabled = true;
    }
  }
}

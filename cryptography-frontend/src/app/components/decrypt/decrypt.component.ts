import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FileToEncrypt} from "../../model/file-to-encrypt";
import {AgGridAngular} from "ag-grid-angular";
import {ColumnApi, GridApi, GridOptions} from "ag-grid-community";
import {Subject} from "rxjs";
import {FilesService} from "../../services/files.service";
import {DownloadButton} from "../ag-grid-component/button-cell-download.components";
import {DownloadDisabledButton} from "../ag-grid-component/button-cell-download-disabled.components";
import {DecryptComponentsDTO} from "../../dto/decrypt-components";

@Component({
  selector: 'app-decrypt',
  templateUrl: './decrypt.component.html',
  styleUrls: ['./decrypt.component.css']
})
export class DecryptComponent implements OnInit {

  @ViewChild("fileToDecrypt") fileToDecrypt: ElementRef;
  @ViewChild("fileId") fileId: ElementRef;
  @ViewChild("keyNumber") keyNumber: ElementRef;
  @ViewChild("agGrid") agGrid: AgGridAngular;

  decryptComponentsDTO: DecryptComponentsDTO;
  isDecryptDisabled: boolean = true;
  isAnyError: boolean = false;
  isClearDisabled: boolean = true;
  isDecryptionDone: boolean = false;

  selectedFile: File = null;
  filesToDecrypt = new Array<FileToEncrypt>();
  rowData: any[];
  columnApi: ColumnApi;
  gridApi: GridApi;

  private dataUpdated$ = new Subject<void>();

  gridOptions: GridOptions = {
    columnDefs: [
      {headerName: "#", valueGetter: "node.rowIndex + 1", width: 60},
      {headerName: "file name", field: "fileName", width: 150},
      {headerName: "file id", field: "fileId", width: 150},
      {headerName: "Status", field: "fileStatus", width: 110},
      {
        headerName: "Action",
        cellRendererSelector: params => {
          if (params.data.fileStatus === 'Download') {
            return {component: DownloadButton}
          } else {
            return {component: DownloadDisabledButton}
          }
        },
        width: 140,
        cellRendererParams: {
          clicked: (data: any) => {
            if (data.fileStatus === "Download") {
              this.onDownload(data);
            } else {
              alert("You try to download unavailable file");
            }
          }
        },
      },
    ]
  };

  onGridReady = (params) => {
    this.gridApi = params.api;
    this.columnApi = params.columnApi;
    this.gridApi.sizeColumnsToFit();
    this.getAllData();
  }

  ngOnInit(): void {
    this.isDecryptDisabled = true;
    this.dataUpdated$.subscribe(() => {
      this.getAllData();
    })
  }

  ngOnDestroy() {
    this.dataUpdated$.unsubscribe();
  }

  constructor(private fileUploadService: FilesService) {
  }

  insertFile(event: any) {
    if (this.filesToDecrypt.length === 0) {
      this.selectedFile = event.target.files[0];
      this.checkValidForm();
      this.isClearDisabled = false;
    } else {
      this.isAnyError = true;
    }
    setTimeout(() => {
      this.isAnyError = false;
    }, 5000)
  }

  clearForm() {
    this.fileToDecrypt.nativeElement.value = null;
    this.fileId.nativeElement.value = "";
    this.keyNumber.nativeElement.value = "";

    if (this.selectedFile !== null) {
      this.selectedFile = null;
    }
    this.isDecryptDisabled = true;
    this.isClearDisabled = true;
    this.isAnyError = false;
    this.filesToDecrypt = [];
  }

  onDecrypt() {
    this.decryptComponentsDTO = new DecryptComponentsDTO();
    this.decryptComponentsDTO.file = this.selectedFile;
    this.decryptComponentsDTO.fileId = this.fileId.nativeElement.value;
    this.decryptComponentsDTO.keyNo = this.keyNumber.nativeElement.value;

    console.log("this is what I send");
    console.log(this.decryptComponentsDTO);

    // this.fileEncryptService.encrypt(fileModel).subscribe(() => {
    //   this.getAllData();
    //   this.refreshData()
    // });
    // this.isEncryptionDone = true;
    //
    // setTimeout(() => {
    //   this.isEncryptionDone = false;
    // }, 5000)

  }

  checkValidForm() {
    // CHECK FOR DecryptButton
    if (this.selectedFile !== null && this.fileId.nativeElement.value.length && this.keyNumber.nativeElement.value.length) {
      this.isDecryptDisabled = false;
    } else {
      this.isDecryptDisabled = true;
    }

    // CHECK FOR ClearButton
    if(this.selectedFile !== null || this.keyNumber.nativeElement.value.length || this.fileId.nativeElement.value.length) {
      this.isClearDisabled = false;
    } else {
      this.isClearDisabled = true;
    }
  }

  private onDownload(data: any) {
    alert("download works");
  }

  getAllData(): void {
    this.fileUploadService.getAllDecryptedFiles().subscribe(data => {
      this.rowData = data;
    });
  }
}

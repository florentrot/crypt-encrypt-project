
import {ICellRendererAngularComp} from "ag-grid-angular";
import {Component} from "@angular/core";
import {ICellRendererParams} from "ag-grid-community";

@Component({
  selector: 'btn-cell-encrypted',
  template: `
    <button (click)="btnClickedHandler()" class="btn btn-outline-dark btn-sm" disabled>Encrypted</button>
  `,

})
export class EncryptedButton implements ICellRendererAngularComp {
  private params: any;

  agInit(params: any): void {
    this.params = params;
  }

  btnClickedHandler() {
    this.params.clicked(this.params.data);
  }

  refresh(params: ICellRendererParams<any>): boolean {
    return false;
  }
}

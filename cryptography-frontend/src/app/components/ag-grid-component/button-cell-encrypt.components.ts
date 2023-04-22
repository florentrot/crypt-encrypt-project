
import {ICellRendererAngularComp} from "ag-grid-angular";
import {Component} from "@angular/core";
import {ICellRendererParams} from "ag-grid-community";

@Component({
  selector: 'btn-cell-encrypt',
  template: `
    <button (click)="btnClickedHandler()" class="btn btn-primary btn-sm">Encrypt</button>
  `,

})
export class EncryptButton implements ICellRendererAngularComp {
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

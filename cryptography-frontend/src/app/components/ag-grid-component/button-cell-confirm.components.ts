import {ICellRendererAngularComp} from "ag-grid-angular";
import {Component} from "@angular/core";
import {ICellRendererParams} from "ag-grid-community";

@Component({
  selector: 'btn-cell-renderer',
  template: `
    <button (click)="btnClickedHandler()" class="btn btn-success btn-sm">Save</button>
  `,

})
export class ConfirmButton implements ICellRendererAngularComp {
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

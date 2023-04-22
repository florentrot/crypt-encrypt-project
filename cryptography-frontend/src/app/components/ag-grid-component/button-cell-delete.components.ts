import {ICellRendererAngularComp} from "ag-grid-angular";
import {Component} from "@angular/core";
import {ICellRendererParams} from "ag-grid-community";

@Component({
  selector: 'btn-cell-delete',
  template: `
    <button (click)="btnClickedHandler()" class="btn btn-danger btn-sm">Delete</button>
  `,

})
export class DeleteButton implements ICellRendererAngularComp {
  private params: any;

  agInit(params: ICellRendererParams): void {
    this.params = params;
  }

  btnClickedHandler() {
    this.params.clicked(this.params.data);
  }

  refresh(params: ICellRendererParams<any>): boolean {
    return false;
  }
}

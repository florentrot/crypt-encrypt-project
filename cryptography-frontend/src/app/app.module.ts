import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { EncryptComponent } from './components/encrypt/encrypt.component';
import { DecryptComponent } from './components/decrypt/decrypt.component';
import {RouterModule, Routes} from "@angular/router";
import { HowToUseComponent } from './components/how-to-use/how-to-use.component';
import { HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {AgGridModule} from "ag-grid-angular";
import {DeleteButton} from "./components/ag-grid-component/button-cell-delete.components";
import {ConfirmButton} from "./components/ag-grid-component/button-cell-confirm.components";
import {EncryptButton} from "./components/ag-grid-component/button-cell-encrypt.components";
import {EncryptedButton} from "./components/ag-grid-component/button-cell-encrypted.components";

const routes: Routes = [
  {path: 'howToUse', component: HowToUseComponent},
  {path: 'encrypt', component: EncryptComponent},
  {path: 'decrypt', component: DecryptComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    EncryptComponent,
    DecryptComponent,
    HowToUseComponent,
    ConfirmButton,
    DeleteButton,
    EncryptButton,
    EncryptedButton
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AgGridModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

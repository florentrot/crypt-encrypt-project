import {FileDTO} from "./file-dto";

export class FileDecryptDto extends FileDTO{

  keyNo?: string;
  file?: File;
}

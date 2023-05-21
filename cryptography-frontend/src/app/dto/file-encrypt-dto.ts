import {FileDTO} from "./file-dto";

export class FileEncryptDto extends FileDTO{

  appFileName?: number;
  fileStatus?: string;
  file?: File;
}

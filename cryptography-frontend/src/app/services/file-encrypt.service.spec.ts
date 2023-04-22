import { TestBed } from '@angular/core/testing';

import { FileEncryptService } from './file-encrypt.service';

describe('FileEncryptService', () => {
  let service: FileEncryptService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FileEncryptService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

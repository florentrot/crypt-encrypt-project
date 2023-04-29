import { TestBed } from '@angular/core/testing';

import { FileDecryptService } from './file-decrypt.service';

describe('FileDecryptService', () => {
  let service: FileDecryptService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FileDecryptService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

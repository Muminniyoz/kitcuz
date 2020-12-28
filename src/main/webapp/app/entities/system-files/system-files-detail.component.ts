import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISystemFiles } from 'app/shared/model/system-files.model';

@Component({
  selector: 'jhi-system-files-detail',
  templateUrl: './system-files-detail.component.html',
})
export class SystemFilesDetailComponent implements OnInit {
  systemFiles: ISystemFiles | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ systemFiles }) => (this.systemFiles = systemFiles));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}

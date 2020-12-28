import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ISystemFiles, SystemFiles } from 'app/shared/model/system-files.model';
import { SystemFilesService } from './system-files.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-system-files-update',
  templateUrl: './system-files-update.component.html',
})
export class SystemFilesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    hashName: [null, []],
    type: [null, [Validators.maxLength(5)]],
    file: [],
    fileContentType: [],
    time: [],
    fileGroup: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected systemFilesService: SystemFilesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ systemFiles }) => {
      if (!systemFiles.id) {
        const today = moment().startOf('day');
        systemFiles.time = today;
      }

      this.updateForm(systemFiles);
    });
  }

  updateForm(systemFiles: ISystemFiles): void {
    this.editForm.patchValue({
      id: systemFiles.id,
      name: systemFiles.name,
      hashName: systemFiles.hashName,
      type: systemFiles.type,
      file: systemFiles.file,
      fileContentType: systemFiles.fileContentType,
      time: systemFiles.time ? systemFiles.time.format(DATE_TIME_FORMAT) : null,
      fileGroup: systemFiles.fileGroup,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('kitcuzApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const systemFiles = this.createFromForm();
    if (systemFiles.id !== undefined) {
      this.subscribeToSaveResponse(this.systemFilesService.update(systemFiles));
    } else {
      this.subscribeToSaveResponse(this.systemFilesService.create(systemFiles));
    }
  }

  private createFromForm(): ISystemFiles {
    return {
      ...new SystemFiles(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      hashName: this.editForm.get(['hashName'])!.value,
      type: this.editForm.get(['type'])!.value,
      fileContentType: this.editForm.get(['fileContentType'])!.value,
      file: this.editForm.get(['file'])!.value,
      time: this.editForm.get(['time'])!.value ? moment(this.editForm.get(['time'])!.value, DATE_TIME_FORMAT) : undefined,
      fileGroup: this.editForm.get(['fileGroup'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISystemFiles>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}

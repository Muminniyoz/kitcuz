import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProjects, Projects } from 'app/shared/model/projects.model';
import { ProjectsService } from './projects.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-projects-update',
  templateUrl: './projects-update.component.html',
})
export class ProjectsUpdateComponent implements OnInit {
  isSaving = false;
  createdDateDp: any;

  editForm = this.fb.group({
    id: [],
    title: [],
    about: [],
    fileUrl: [],
    createdDate: [],
    isShowing: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected projectsService: ProjectsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projects }) => {
      this.updateForm(projects);
    });
  }

  updateForm(projects: IProjects): void {
    this.editForm.patchValue({
      id: projects.id,
      title: projects.title,
      about: projects.about,
      fileUrl: projects.fileUrl,
      createdDate: projects.createdDate,
      isShowing: projects.isShowing,
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
    const projects = this.createFromForm();
    if (projects.id !== undefined) {
      this.subscribeToSaveResponse(this.projectsService.update(projects));
    } else {
      this.subscribeToSaveResponse(this.projectsService.create(projects));
    }
  }

  private createFromForm(): IProjects {
    return {
      ...new Projects(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      about: this.editForm.get(['about'])!.value,
      fileUrl: this.editForm.get(['fileUrl'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      isShowing: this.editForm.get(['isShowing'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjects>>): void {
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

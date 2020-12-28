import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGalereya, Galereya } from 'app/shared/model/galereya.model';
import { GalereyaService } from './galereya.service';

@Component({
  selector: 'jhi-galereya-update',
  templateUrl: './galereya-update.component.html',
})
export class GalereyaUpdateComponent implements OnInit {
  isSaving = false;
  createdDateDp: any;

  editForm = this.fb.group({
    id: [],
    title: [],
    createdDate: [],
  });

  constructor(protected galereyaService: GalereyaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ galereya }) => {
      this.updateForm(galereya);
    });
  }

  updateForm(galereya: IGalereya): void {
    this.editForm.patchValue({
      id: galereya.id,
      title: galereya.title,
      createdDate: galereya.createdDate,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const galereya = this.createFromForm();
    if (galereya.id !== undefined) {
      this.subscribeToSaveResponse(this.galereyaService.update(galereya));
    } else {
      this.subscribeToSaveResponse(this.galereyaService.create(galereya));
    }
  }

  private createFromForm(): IGalereya {
    return {
      ...new Galereya(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGalereya>>): void {
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

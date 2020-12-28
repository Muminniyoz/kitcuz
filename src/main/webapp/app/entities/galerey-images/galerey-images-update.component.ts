import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGalereyImages, GalereyImages } from 'app/shared/model/galerey-images.model';
import { GalereyImagesService } from './galerey-images.service';
import { IGalereya } from 'app/shared/model/galereya.model';
import { GalereyaService } from 'app/entities/galereya/galereya.service';

@Component({
  selector: 'jhi-galerey-images-update',
  templateUrl: './galerey-images-update.component.html',
})
export class GalereyImagesUpdateComponent implements OnInit {
  isSaving = false;
  galereyas: IGalereya[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    imageUrl: [],
    number: [],
    galereyId: [],
  });

  constructor(
    protected galereyImagesService: GalereyImagesService,
    protected galereyaService: GalereyaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ galereyImages }) => {
      this.updateForm(galereyImages);

      this.galereyaService.query().subscribe((res: HttpResponse<IGalereya[]>) => (this.galereyas = res.body || []));
    });
  }

  updateForm(galereyImages: IGalereyImages): void {
    this.editForm.patchValue({
      id: galereyImages.id,
      title: galereyImages.title,
      imageUrl: galereyImages.imageUrl,
      number: galereyImages.number,
      galereyId: galereyImages.galereyId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const galereyImages = this.createFromForm();
    if (galereyImages.id !== undefined) {
      this.subscribeToSaveResponse(this.galereyImagesService.update(galereyImages));
    } else {
      this.subscribeToSaveResponse(this.galereyImagesService.create(galereyImages));
    }
  }

  private createFromForm(): IGalereyImages {
    return {
      ...new GalereyImages(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      number: this.editForm.get(['number'])!.value,
      galereyId: this.editForm.get(['galereyId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGalereyImages>>): void {
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

  trackById(index: number, item: IGalereya): any {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGalereyImages } from 'app/shared/model/galerey-images.model';

@Component({
  selector: 'jhi-galerey-images-detail',
  templateUrl: './galerey-images-detail.component.html',
})
export class GalereyImagesDetailComponent implements OnInit {
  galereyImages: IGalereyImages | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ galereyImages }) => (this.galereyImages = galereyImages));
  }

  previousState(): void {
    window.history.back();
  }
}

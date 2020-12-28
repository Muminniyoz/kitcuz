export interface IGalereyImages {
  id?: number;
  title?: string;
  imageUrl?: string;
  number?: string;
  galereyId?: number;
}

export class GalereyImages implements IGalereyImages {
  constructor(public id?: number, public title?: string, public imageUrl?: string, public number?: string, public galereyId?: number) {}
}

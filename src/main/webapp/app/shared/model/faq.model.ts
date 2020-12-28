export interface IFaq {
  id?: number;
  orderNumber?: number;
  question?: string;
  answer?: string;
  active?: boolean;
}

export class Faq implements IFaq {
  constructor(public id?: number, public orderNumber?: number, public question?: string, public answer?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}

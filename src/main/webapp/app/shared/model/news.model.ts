import { Moment } from 'moment';

export interface INews {
  id?: number;
  title?: string;
  shortText?: string;
  fullText?: any;
  createdDate?: Moment;
  active?: boolean;
  imageUrl?: string;
}

export class News implements INews {
  constructor(
    public id?: number,
    public title?: string,
    public shortText?: string,
    public fullText?: any,
    public createdDate?: Moment,
    public active?: boolean,
    public imageUrl?: string
  ) {
    this.active = this.active || false;
  }
}

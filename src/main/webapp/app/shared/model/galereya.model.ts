import { Moment } from 'moment';

export interface IGalereya {
  id?: number;
  title?: string;
  createdDate?: Moment;
}

export class Galereya implements IGalereya {
  constructor(public id?: number, public title?: string, public createdDate?: Moment) {}
}

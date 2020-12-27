import { Moment } from 'moment';

export interface IProjects {
  id?: number;
  title?: string;
  about?: any;
  fileUrl?: string;
  createdDate?: Moment;
  isShowing?: boolean;
}

export class Projects implements IProjects {
  constructor(
    public id?: number,
    public title?: string,
    public about?: any,
    public fileUrl?: string,
    public createdDate?: Moment,
    public isShowing?: boolean
  ) {
    this.isShowing = this.isShowing || false;
  }
}

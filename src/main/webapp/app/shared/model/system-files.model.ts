import { Moment } from 'moment';

export interface ISystemFiles {
  id?: number;
  name?: string;
  type?: string;
  fileContentType?: string;
  file?: any;
  time?: Moment;
}

export class SystemFiles implements ISystemFiles {
  constructor(
    public id?: number,
    public name?: string,
    public type?: string,
    public fileContentType?: string,
    public file?: any,
    public time?: Moment
  ) {}
}

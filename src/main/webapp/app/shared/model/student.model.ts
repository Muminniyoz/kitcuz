import { Moment } from 'moment';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IStudent {
  id?: number;
  firstName?: string;
  lastName?: string;
  middleName?: string;
  email?: string;
  dateOfBirth?: Moment;
  gender?: Gender;
  registerationDate?: Moment;
  lastAccess?: Moment;
  telephone?: string;
  mobile?: string;
  thumbnailPhotoUrl?: string;
  fullPhotoUrl?: string;
  active?: boolean;
  key?: string;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public middleName?: string,
    public email?: string,
    public dateOfBirth?: Moment,
    public gender?: Gender,
    public registerationDate?: Moment,
    public lastAccess?: Moment,
    public telephone?: string,
    public mobile?: string,
    public thumbnailPhotoUrl?: string,
    public fullPhotoUrl?: string,
    public active?: boolean,
    public key?: string
  ) {
    this.active = this.active || false;
  }
}

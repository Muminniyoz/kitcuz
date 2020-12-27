import { Moment } from 'moment';

export interface IAbilityStudent {
  id?: number;
  firstName?: string;
  lastName?: string;
  middleName?: string;
  about?: any;
  email?: string;
  dateOfBirth?: Moment;
  registerationDate?: Moment;
  telephone?: string;
  mobile?: string;
  thumbnailPhotoUrl?: string;
  fullPhotoUrl?: string;
  isShowing?: boolean;
}

export class AbilityStudent implements IAbilityStudent {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public middleName?: string,
    public about?: any,
    public email?: string,
    public dateOfBirth?: Moment,
    public registerationDate?: Moment,
    public telephone?: string,
    public mobile?: string,
    public thumbnailPhotoUrl?: string,
    public fullPhotoUrl?: string,
    public isShowing?: boolean
  ) {
    this.isShowing = this.isShowing || false;
  }
}

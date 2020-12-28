import { Moment } from 'moment';
import { ISkill } from 'app/shared/model/skill.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface ITeacher {
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
  about?: string;
  portfolia?: any;
  leaveDate?: Moment;
  isShowingHome?: boolean;
  imageUrl?: string;
  skills?: ISkill[];
}

export class Teacher implements ITeacher {
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
    public key?: string,
    public about?: string,
    public portfolia?: any,
    public leaveDate?: Moment,
    public isShowingHome?: boolean,
    public imageUrl?: string,
    public skills?: ISkill[]
  ) {
    this.active = this.active || false;
    this.isShowingHome = this.isShowingHome || false;
  }
}

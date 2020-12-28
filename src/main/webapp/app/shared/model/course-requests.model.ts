import { Moment } from 'moment';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface ICourseRequests {
  id?: number;
  firstName?: string;
  lastName?: string;
  middleName?: string;
  email?: string;
  dateOfBirth?: Moment;
  gender?: Gender;
  registerationDate?: Moment;
  telephone?: string;
  mobile?: string;
  coursesId?: number;
  coursesId?: number;
}

export class CourseRequests implements ICourseRequests {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public middleName?: string,
    public email?: string,
    public dateOfBirth?: Moment,
    public gender?: Gender,
    public registerationDate?: Moment,
    public telephone?: string,
    public mobile?: string,
    public coursesId?: number,
    public coursesId?: number
  ) {}
}

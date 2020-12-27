import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'system-files',
        loadChildren: () => import('./system-files/system-files.module').then(m => m.KitcuzSystemFilesModule),
      },
      {
        path: 'courses',
        loadChildren: () => import('./courses/courses.module').then(m => m.KitcuzCoursesModule),
      },
      {
        path: 'skill',
        loadChildren: () => import('./skill/skill.module').then(m => m.KitcuzSkillModule),
      },
      {
        path: 'teacher',
        loadChildren: () => import('./teacher/teacher.module').then(m => m.KitcuzTeacherModule),
      },
      {
        path: 'planning',
        loadChildren: () => import('./planning/planning.module').then(m => m.KitcuzPlanningModule),
      },
      {
        path: 'course-group',
        loadChildren: () => import('./course-group/course-group.module').then(m => m.KitcuzCourseGroupModule),
      },
      {
        path: 'theme',
        loadChildren: () => import('./theme/theme.module').then(m => m.KitcuzThemeModule),
      },
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.KitcuzStudentModule),
      },
      {
        path: 'student-group',
        loadChildren: () => import('./student-group/student-group.module').then(m => m.KitcuzStudentGroupModule),
      },
      {
        path: 'projects',
        loadChildren: () => import('./projects/projects.module').then(m => m.KitcuzProjectsModule),
      },
      {
        path: 'ability-student',
        loadChildren: () => import('./ability-student/ability-student.module').then(m => m.KitcuzAbilityStudentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class KitcuzEntityModule {}

import { Routes } from '@angular/router';
import { HomeComponent } from './page/home/home.component'; // Certifique-se de que a importação está correta
import { LoginComponent } from './page/login/login.component';
import { SignupComponent } from './page/signup/signup.component';
import { InicilComponent } from './page/inicil/inicil.component';
import { MoodEntryComponent } from './page/mood-entry/mood-entry.component';
import { BuscaComponent } from './page/busca/busca.component';


export const routes: Routes = [
  {
    path: '',
    redirectTo: '/home', // Redirecionando para a página Home como inicial
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
    title: 'Login'
  },
  {
    path: 'singup',
    component: SignupComponent,
    title: 'Cadastro'
  },
  {
    path: 'home',
    component: HomeComponent,
    title: 'Homepage'
  },
  {
    path: 'inicil',
    component: InicilComponent,
    title: 'inicilpage'
  },
  {
    path: 'mood-entry',
    component: MoodEntryComponent,  
    title: 'Como estou me sentindo hoje?'
  },
  {
    path: 'buscar',
    component: BuscaComponent,
    title: 'Buscar'
  }
  
];

export default routes;


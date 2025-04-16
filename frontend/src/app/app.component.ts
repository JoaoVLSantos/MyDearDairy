import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule],
  template: `
    <main>
      <div class="content">
        <router-outlet></router-outlet>
      </div>
    </main>
  `,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'My Dear Diary';
}


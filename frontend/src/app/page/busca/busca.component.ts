import { Component } from '@angular/core';

@Component({
  selector: 'app-busca',
  templateUrl: './busca.component.html',
  styleUrls: ['./busca.component.css']
})
export class BuscaComponent {

  searchBy: string = 'date';  // Filtro inicial (Data)
  filteredEntries: any[] = [];  // Entradas filtradas para exibição

  // Simulação de dados do diário
  entries = [
    { date: '2025-04-15', name: 'Dia Alegre', mood: 'Alegre' },
    { date: '2025-04-16', name: 'Dia Triste', mood: 'Triste' },
    { date: '2025-04-17', name: 'Dia Pensativo', mood: 'Pensativo' },
    { date: '2025-04-18', name: 'Dia Muito Alegre', mood: 'Muito Alegre' }
  ];

  // Função para alternar a busca
  setSearchBy(option: string) {
    this.searchBy = option;
    this.filteredEntries = [];  // Limpar resultados ao mudar a opção
  }

  // Função de busca
  performSearch(searchDate: string, searchKeyword: string) {
    if (this.searchBy === 'date' && searchDate) {
      this.filteredEntries = this.entries.filter(entry => entry.date === searchDate);
    } else if (this.searchBy === 'keyword' && searchKeyword) {
      this.filteredEntries = this.entries.filter(entry => 
        entry.name.toLowerCase().includes(searchKeyword.toLowerCase()) || 
        entry.mood.toLowerCase().includes(searchKeyword.toLowerCase())
      );
    }

    // Se não houver resultados, assegure que filteredEntries esteja vazio para mostrar a mensagem
    if (this.filteredEntries.length === 0) {
      this.noResultsMessage = true;
    } else {
      this.noResultsMessage = false;
    }
  }

  // Variável para controlar a visibilidade da mensagem de "Nenhuma Entrada"
  noResultsMessage: boolean = false;
}


// Função para filtrar por continente
function filterByContinent() {
  const continentSelect = document.getElementById("continent-filter");
  const selectedContinent = continentSelect.value;
  const sortSelect = document.getElementById("sort-by");
  const sortBy = sortSelect.value;

  // Constrói a URL com os parâmetros de filtro
  let url = "/countries?";

  if (selectedContinent) {
    url += `continent=${selectedContinent}`;
  }

  if (sortBy) {
    url += `${selectedContinent ? "&" : ""}sortBy=${sortBy}`;
  }

  // Redireciona para a URL com os filtros aplicados
  window.location.href = url;
}

// Função para alterar a ordenação
function changeSorting() {
  const continentSelect = document.getElementById("continent-filter");
  const selectedContinent = continentSelect.value;
  const sortSelect = document.getElementById("sort-by");
  const sortBy = sortSelect.value;

  // Constrói a URL com os parâmetros de filtro e ordenação
  let url = "/countries?";

  if (selectedContinent) {
    url += `continent=${selectedContinent}&`;
  }

  url += `sortBy=${sortBy}`;

  // Redireciona para a URL com os filtros e ordenação aplicados
  window.location.href = url;
}

// Função para resetar os filtros
function resetFilters() {
  window.location.href = "/countries";
}

// Adiciona efeito de hover nas bandeiras
document.addEventListener("DOMContentLoaded", function () {
  const countryCards = document.querySelectorAll(".country-card");

  countryCards.forEach((card) => {
    card.addEventListener("mouseenter", function () {
      this.style.transform = "translateY(-10px)";
    });

    card.addEventListener("mouseleave", function () {
      this.style.transform = "translateY(0)";
    });
  });
});

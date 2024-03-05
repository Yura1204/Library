import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import CatalogComponent from './components/CatalogComponent';
import BookDetails from './components/BookDetails';
import AuthorDetails from './components/AuthorDetails';
import AddBookForm from './components/AddBookForm';

const App: React.FC = () => {
  return (
    <Router>
      <div>
        <h1>BookLibrary</h1>
        <Routes>
          <Route path='/' Component={CatalogComponent}/>
          <Route path='/books/:id' Component={BookDetails}/>
          <Route path="/author/:id" Component={AuthorDetails} />
          <Route path='/books/add' Component={AddBookForm}/>
        </Routes>
      </div>
    </Router>
  );
};

export default App;
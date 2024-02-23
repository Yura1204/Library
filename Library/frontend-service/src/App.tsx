import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import CatalogComponent from './components/CatalogComponent';
import BookDetails from './components/BookDetails';

const App: React.FC = () => {
  return (
    <Router>
      <div>
        <h1>BookLibrary</h1>
        <Routes>
          <Route path='/' Component={CatalogComponent}/>
          <Route path='/books/:id' Component={BookDetails}/>
        </Routes>
      </div>
    </Router>
  );
};

export default App;
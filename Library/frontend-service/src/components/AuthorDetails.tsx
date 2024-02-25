import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

interface Author {
  authorId: number;
  authorname: string;
  biography: string;
}

const AuthorDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>(); // Получаем id из URL
  const [author, setAuthor] = useState<Author | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      const response = await axios.get<Author>(`http://localhost:8080/catalogservice/api/authors/author/${id}`);
      setAuthor(response.data);
    };

    fetchData();
  }, [id]); // Запускаем эффект при изменении id

  if (!author) {
    return <div>Загрузка...</div>;
  }

  return (
    <div>
      <h1>{author.authorname}</h1>
      <p>{author.biography}</p>
    </div>
  );
};

export default AuthorDetails;
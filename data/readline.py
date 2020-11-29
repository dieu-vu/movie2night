import csv
import os
import json

DIR = os.path.dirname(os.path.abspath(__file__))
#tsv_files = [f for f in os.listdir(DIR) if f.endswith('-lite.tsv')]
#for tsv in tsv_files:
basic_data = 'title.basics.tsv' 
rating = 'title.ratings.tsv'
json_name = basic_data.replace('.tsv', '.json')

with open(rating) as f:
    count = 0
    line = f.readline()
    rating_dict = {}
    while line:
        data = line.strip().split('\t')
        try:
            movie_id = data[0]
        except ValueError as e:
            continue
        rating_dict[movie_id] = data[1]
        line = f.readline()
print(rating_dict)

with open(basic_data) as f:
    final_data = {}
    header = f.readline()
    fieldnames = header.strip().split('\t')
    #f.seek(100, 0)
    #f.seek(f.tell() - 50000, os.SEEK_SET)
    print(fieldnames)
    counter = 0
    while counter <= 100:
        line = f.readline()
        data = line.strip().split('\t')
        try:
            movie_id = data[0]
            year = int(data[5])
            movie_type = data[1]
        except ValueError as e:
            continue
        if (year <= 2000 or movie_type !='movie' or (movie_id not in rating_dict.keys())):
            continue
        counter += 1 
        data_dict = {fieldnames[i]: data[i] for i in range(len(fieldnames))}
        movie_id = data_dict['tconst']
        data_dict['rating'] = rating_dict[movie_id]
        print(data_dict)
        final_data[movie_id] = data_dict
print(final_data)
with open(json_name, 'w') as f:
    json.dump(final_data, f, indent=4)


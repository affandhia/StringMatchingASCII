import random
import sys
import os

def getFileContent(pathAndFileName):
    with open(pathAndFileName, 'r') as theFile:
        data = theFile.read().split('\n')
        return data

def generateName():
    name_depans = getFileContent('./name_first.txt')
    names = getFileContent('./name_raw.txt')

    # ambil namanya
    count = random.randrange(1, 4)
    name_final = random.sample(names, count)

    # gabungkan jadi username
    username = ""
    separator = ""
    # gabung pake dot atau tidak secara acak
    if random.random() >= 0.5:
        separator = "."
    else:
        separator = ""

    username = separator.join(name_final)

    if count < 3 and random.random() >= 0.5:
        fname_separator = "." if separator == "." else "."
        username = random.sample(name_depans, 1)[0] + fname_separator + username

    # kasih angka dibelakang secara acak
    if random.random() >= 0.5:
        username = username + str(random.randrange(0, 999))

    return username.lower()

def writeToFile(list):
    pathname = f'dist/usernames-{len(list)}.txt'
    os.makedirs(os.path.dirname(pathname), exist_ok=True)
    with open(pathname, 'w') as the_file:
        for name in list:
            the_file.write(f'{name}\n')

def main():
    n = 10

    try:
        n = int(sys.argv[1])
    except:
        pass
    
    print("generating...")
    distinct = set()
    while len(distinct) < n:
        distinct.add(generateName())

    print("writing...")
    writeToFile(distinct)

if __name__ == '__main__':
    main()
const Mongoose = require("mongoose");
const Part = Mongoose.model("Part");
const Bicycle = Mongoose.model("Bicycle");

Part.deleteMany({})
  .then(function () {
    console.log("Data deleted");
  })
  .catch(function (error) {
    console.log(error);
  });
Bicycle.deleteMany({})
  .then(function () {
    console.log("Data deleted");
  })
  .catch(function (error) {
    console.log(error);
  });

Part.insertMany([
  {
    name: "Bicycle Part 1",
    description:
      "A seatpost wedge compatible with the 2019 S3 Rim and Disc models.",
    compatibilities: ["Bicycle D"],
    imageUrl: ["/assets/part-1-1.png", "/assets/part-1-2.png"],
  },
  {
    name: "Bicycle Part 2",
    description:
      'As their name implies, the Short Reach handlebars are designed for those that require a shorter reach in order to obtain the optimal bike fit. Along these lines, they feature a 65mm reach (which is about 10 to 15mm shorter than "average") and a shallow 125mm drop. This also comes with the added benefit of increased control at the hoods and levers. And for the construction, we selected a lightweight, yet highly durable, 6061 Premium Butted Aluminum that\'s sure to stand up to years of hard riding. 6062 premium butted aluminum, high-strength design. Short reach for optimal brake/shift control. Shallow Bend Drop: 125mm drop x 65mm reach',
    compatibilities: ["Bicycle A", "Bicycle B"],
    imageUrl: ["/assets/part-2-1.png", "/assets/part-2-2.png"],
  },
  {
    name: "Bicycle Part 3",
    description:
      "A seatpost wedge compatible with the 2019 S3 Rim and Disc models.",
    compatibilities: ["Bicycle B", "Bicycle C"],
    imageUrl: ["/assets/part-1-1.png", "/assets/part-1-2.png"],
  },
  {
    name: "Bicycle Part 4",
    description:
      'As their name implies, the Short Reach handlebars are designed for those that require a shorter reach in order to obtain the optimal bike fit. Along these lines, they feature a 65mm reach (which is about 10 to 15mm shorter than "average") and a shallow 125mm drop. This also comes with the added benefit of increased control at the hoods and levers. And for the construction, we selected a lightweight, yet highly durable, 6061 Premium Butted Aluminum that\'s sure to stand up to years of hard riding. 6062 premium butted aluminum, high-strength design. Short reach for optimal brake/shift control. Shallow Bend Drop: 125mm drop x 65mm reach',
    compatibilities: ["Bicycle A", "Bicycle B", "Bicycle C"],
    imageUrl: ["/assets/part-2-1.png", "/assets/part-2-2.png"],
  },
]).then((data) => {
  console.log(data);
});

Bicycle.insertMany([
  {
    name: "Bicycle A",
    imageUrl: [
      "/assets/bicycle-a-1.png",
      "/assets/bicycle-a-2.png",
      "/assets/bicycle-a-3.png",
    ],
  },
  {
    name: "Bicycle B",
    imageUrl: [
      "/assets/bicycle-b-1.png",
      "/assets/bicycle-b-2.png",
      "/assets/bicycle-b-3.png",
    ],
  },
  {
    name: "Bicycle C",
    imageUrl: [
      "/assets/bicycle-a-1.png",
      "/assets/bicycle-a-2.png",
      "/assets/bicycle-a-3.png",
    ],
  },
  {
    name: "Bicycle D",
    imageUrl: [
      "/assets/bicycle-b-1.png",
      "/assets/bicycle-b-2.png",
      "/assets/bicycle-b-3.png",
    ],
  },
]).then((data) => {
  console.log(data);
});

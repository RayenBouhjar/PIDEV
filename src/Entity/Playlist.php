<?php

namespace App\Entity;

use App\Repository\PlaylistRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


/**
 * @ORM\Entity(repositoryClass=PlaylistRepository::class)
 */
class Playlist
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\Length(min=3,minMessage="Votre nom est trop court",max=10,maxMessage="Votre nom est trop long")
     */
    private $nom;

    /**
     * @ORM\Column(type="date")
     */
    private $date;

    /**
     * @ORM\OneToMany(targetEntity=Pist::class, mappedBy="playlist",cascade={"all"},orphanRemoval=true)
     */
    private $pists;

    public function __construct()
    {
        $this->pists = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    /**
     * @return Collection|Pist[]
     */
    public function getPists(): Collection
    {
        return $this->pists;
    }

    public function addPist(Pist $pist): self
    {
        if (!$this->pists->contains($pist)) {
            $this->pists[] = $pist;
            $pist->setPlaylist($this);
        }

        return $this;
    }

    public function removePist(Pist $pist): self
    {
        if ($this->pists->removeElement($pist)) {
            // set the owning side to null (unless already changed)
            if ($pist->getPlaylist() === $this) {
                $pist->setPlaylist(null);
            }
        }

        return $this;
    }
}
